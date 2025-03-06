import React from "react";
import Card from "./Card";

const CardContainerDeck = ({ editedCards, addCard, removeCard }) => {
    console.log("editedCards:", editedCards); // Ver el contenido de editedCards

    return (
        <div className="p-4">
            <div className="grid grid-cols-2 gap-4">
                {/* Iterar directamente sobre editedCards */}
                {editedCards.map((card) => (
                     <div key={card.id} className="flex flex-col items-center">
                     <Card 
                         card={card} 
                         addCard={() => addCard(card)} 
                         removeCard={() => removeCard(card.id)} 
                     />
                     <div className="mt-2 text-center">
                         <p>Copias: {card.copies}</p>  {/* Mostrar el atributo copies */}
                     </div>
                 </div>
                ))}
            </div>
        </div>
    );
};

export default CardContainerDeck;
