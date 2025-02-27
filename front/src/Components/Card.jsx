import { useState } from "react";
import { useLocation } from "react-router-dom";
const Card = ({ card, addCard, removeCard }) => {
  const [showDetails, setShowDetails] = useState(false);
  const location = useLocation();
  const isInDecks = location.pathname.includes("decks");
  return (
    <div className="relative cursor-pointer border border-gray-300 p-4 rounded-lg shadow-md bg-gray-800 text-white w-[150px] hover:scale-105 transition-transform"
      onClick={() => setShowDetails(!showDetails)}
    >
      {/* Imagen de la carta */}
      <img src={card.image_url} alt={card.name} className="w-full h-auto rounded-md" />
      {/* Botones en la pagina decks */}
      {isInDecks && (
        <>
          <button
            className="absolute top-32 right-2 bg-white bg-opacity-30 text-black font-bold w-6 h-6 flex items-center justify-center rounded-full hover:bg-opacity-50 transition"
            onClick={(e) => { e.stopPropagation(); addCard(card);}}
          >
            +
          </button>

          <button
            className="absolute top-40 right-2 bg-white bg-opacity-30 text-black font-bold w-6 h-6 flex items-center justify-center rounded-full hover:bg-opacity-50 transition"
            onClick={(e) => { e.stopPropagation(); removeCard(card);}}
          >
            -
          </button>
        </>
      )
      }
      {/* Detalles de la carta */}
      {showDetails && (
        <div className="mt-2">
          <h3 className="text-lg font-bold">{card.name}</h3>
          <p><strong>Ataque:</strong> {card.power}</p>
          <p><strong>Defensa:</strong> {card.id}</p>
          <p><strong>Tipo:</strong> {card.type}</p>
        </div>
      )}
    </div>
  )
}

export default Card;
