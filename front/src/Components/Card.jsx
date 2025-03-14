import { useState } from "react";
import { useLocation } from "react-router-dom";

// Componente Modal
const CardModal = ({ card, onClose }) => {
  return (
    <div className="fixed inset-0 flex items-center justify-center z-50">
    <div className="fixed inset-0 bg-black/40 z-20" onClick={onClose}></div>
    <div className="bg-gray-700 rounded-lg p-4 sm:p-6 flex flex-col sm:flex-row items-center sm:items-stretch w-[90%] max-w-4xl h-auto sm:h-[60%] z-30">
      <div className="w-full sm:w-2/5 flex justify-center items-center mb-4 sm:mb-0">
        <img src={card.image_url} alt={card.name} className="max-h-64 sm:max-h-full object-contain rounded-md" />
      </div>
      <div className="w-full sm:w-3/5 flex flex-col justify-center sm:pl-6 space-y-4 items-center sm:items-start text-center sm:text-left">
        <h3 className="text-2xl sm:text-4xl font-bold">{card.name}</h3>
        <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
          <p className="text-lg sm:text-xl"><strong>Poder:</strong> {card.power}</p>
          <p className="text-lg sm:text-xl"><strong>Counter:</strong> {card.counter}</p>
          <p className="text-lg sm:text-xl"><strong>Tipo:</strong> {card.type}</p>
          <p className="text-lg sm:text-xl"><strong>Color:</strong> {card.color}</p>
        </div>
        <p className="text-md sm:text-lg"><strong>Efecto:</strong> {card.effect}</p>
      </div>
    </div>
  </div>
  );
};

const Card = ({ card, addCard, removeCard }) => {

  const [showModal, setShowModal] = useState(false);

  const [isLoading, setIsLoading] = useState(true);
  const location = useLocation();
  const isInDecks = location.pathname.includes("edit");

  return (
    <>
      <div
        className="relative h-[200px] cursor-pointer border border-gray-300 p-4 rounded-lg shadow-md bg-gray-800 text-white w-[150px] hover:scale-105 transition-transform"
        onClick={() => setShowModal(true)}
      >
        {isLoading && (
          <div className="w-full h-[162px] bg-gray-500 rounded-md"></div>
        )}
        <img
          src={card.image_url}
          alt={card.name}
          className={`w-full h-[162px] rounded-md transition-opacity duration-500 ${
            isLoading ? "opacity-0" : "opacity-100"
          }`}
          onLoad={() => setIsLoading(false)}
        />

        {isInDecks && (
          <>
            <button
              className="absolute top-32 right-2 bg-white bg-opacity-30 text-black font-bold w-6 h-6 flex items-center justify-center rounded-full hover:bg-opacity-50 transition"
              onClick={(e) => { e.stopPropagation(); addCard(card); }}
            >
              +
            </button>

            <button
              className="absolute top-40 right-2 bg-white bg-opacity-30 text-black font-bold w-6 h-6 flex items-center justify-center rounded-full hover:bg-opacity-50 transition"
              onClick={(e) => { e.stopPropagation(); removeCard(card); }}
            >
              -
            </button>
          </>
        )}
      </div>

      {/* Modal de detalles */}
      {showModal && <CardModal card={card} onClose={() => setShowModal(false)} />}
    </>
  );
};

export default Card;
