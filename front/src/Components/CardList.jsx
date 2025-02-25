import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Card from './Card';
import CardFilter from './CardFilter';
const CardList = () => {
    const [cards, setCards] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [filteredCards, setFilteredCards]= useState([])
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
    
      if (loading) return <p>Cargando cartas...</p>;
      if (error) return <p>{error}</p>;
    
      return (
        <div>
          <CardFilter cards={cards} setFilteredCards={setFilteredCards} />
          <div className='flex flex-wrap justify-center p-4'>
            {filteredCards.map((card) => (
              <Card key={card.id} card={card} />
            ))}
          </div>
        </div>
      );
    };

export default CardList;
