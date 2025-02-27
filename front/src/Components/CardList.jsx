import React, { useEffect, useState } from "react";
import axios from "axios";
import Card from "./Card";
import CardFilter from "./CardFilter";
import {useLocation} from "react-router-dom";

const CardList = ({ numPerPage = 10, removeCard, addCard }) => {
  const [cards, setCards] = useState([]);
  const [filteredCards, setFilteredCards] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const location = useLocation()
  const [currentPage, setCurrentPage] = useState(0);
  useEffect(() => {
    axios
      .get("http://localhost:8080/cards/all")
      .then((response) => {
        setCards(response.data);
        setFilteredCards(response.data);
        setLoading(false);
      })
      .catch((error) => {
        console.error("Error al obtener las cartas:", error);
        setError("Error al cargar cartas");
        setLoading(false);
      });
  }, []);

  if (loading) return <p className="text-white text-center">Cargando cartas...</p>;
  if (error) return <p className="text-red-500 text-center">{error}</p>;

  const startIndex = currentPage * numPerPage;
  const endIndex = startIndex + numPerPage;
  const displayedCards = filteredCards.slice(startIndex, endIndex);

  
  const nextPage = () => {
    if (endIndex < filteredCards.length) {
      setCurrentPage(currentPage + 1);
    }
  };

  const prevPage = () => {
    if (currentPage > 0) {
      setCurrentPage(currentPage - 1);
    }
  };

  return (
    <div className="w-full flex flex-col items-center p-4">
      <CardFilter cards={cards} setFilteredCards={setFilteredCards} />
      <div className="grid grid-cols-2 sm:grid-cols-4 md:grid-cols-4 lg:grid-cols-6 gap-4 p-4">
        {displayedCards.map((card) => (
          <Card key={card.id} card={card} removeCard={removeCard} addCard={addCard}  />
        ))}
      </div>

      <div className="flex gap-4 mt-4">
        <button 
          onClick={prevPage} 
          disabled={currentPage === 0} 
          className={`px-4 py-2 rounded bg-blue-500 text-white ${currentPage === 0 ? "opacity-50 cursor-not-allowed" : "hover:bg-blue-700"}`}
        >
          Anterior
        </button>

        <button 
          onClick={nextPage} 
          disabled={endIndex >= filteredCards.length} 
          className={`px-4 py-2 rounded bg-blue-500 text-white ${endIndex >= filteredCards.length ? "opacity-50 cursor-not-allowed" : "hover:bg-blue-700"}`}
        >
          Siguiente
        </button>
      </div>
    </div>
  );
};

export default CardList;
