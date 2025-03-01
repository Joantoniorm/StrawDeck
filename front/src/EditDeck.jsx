import { useParams } from "react-router-dom";
import CardList from "./Components/CardList";
import axios from "axios";
import { useEffect, useState } from "react";
import useDeckCards from "./Hooks/useDeckCards";
import DeckView from "./Components/DeckView";
import Deck from "./Components/Deck";

const EditDeck = () => {
  const { deckId } = useParams();
  const { deckCards,setDeckCards, originalDeckCards,setOriginalDeckCards, leaderImage } = useDeckCards(deckId);

  const [editedCards, setEditedCards] = useState({ Contains: [] });  
  
  //Fetch
  useEffect(() => {
   setEditedCards({Contains:deckCards})
}, [deckCards]);

 
  
  const addCard = (card) => {
    setEditedCards((prev) => {
      const existingCard = prev.Contains.find((c) => c.id === card.id);

      if (existingCard) {
        return {
          ...prev,
          Contains: prev.Contains.map((c) =>
            c.id === card.id ? { ...c, copies: c.copies + 1 } : c
          ),
        };
      } else {
        return {
          ...prev,
          Contains: [...prev.Contains, { ...card, copies: 1 }],
        };
      }
    });
  };

  
  const removeCard = (cardId) => {
    setEditedCards((prev) => ({
      ...prev,
      Contains: prev.Contains
        .map((c) => (c.id === cardId ? { ...c, copies: c.copies - 1 } : c))
        .filter((c) => c.copies > 0),
    }));
  };

  const saveChanges = async () => {
    const toAdd = [];
    const toUpdate = [];
    const toDelete = [];

    const originalMap = new Map(originalDeckCards.map(c => [c.id, c.copies]));

    for (const card of editedCards.Contains) {
        const originalCopies = originalMap.get(card.id);

        if (originalCopies === undefined) {
            toAdd.push({ card_id: card.id, quantity: card.copies });
        } else if (originalCopies !== card.copies) {
            toUpdate.push({ card_id: card.id, quantity: card.copies });
        }

        originalMap.delete(card.id);
    }

    for (const [cardId] of originalMap) {
        toDelete.push({ card_id: cardId });
    }

    try {
        if (toAdd.length > 0) {
            await axios.post(`http://localhost:8080/decks/${deckId}/addCards`, toAdd);
        }

        if (toUpdate.length > 0) {
            await axios.post(`http://localhost:8080/decks/${deckId}/updateCards`, toUpdate);
        }

        if (toDelete.length > 0) {
            await axios.post(`http://localhost:8080/decks/${deckId}/deleteCards`, toDelete);
        }

        console.log("Cambios guardados correctamente");
        setOriginalDeckCards(editedCards.Contains);
    } catch (error) {
        console.error("Error al guardar cambios:", error);
    }
};


  return (
    <div className="p-4">
      <h2 className="text-2xl font-bold text-white text-center">Editar Mazo</h2>
      <CardList addCard={addCard} removeCard={removeCard} />
      
      <DeckView 
          editedCards={editedCards} 
          addCard={addCard} 
          removeCard={removeCard} 
      />

      <div className="text-center mt-4">
        <button 
          className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-700"
          onClick={saveChanges}
        >
          Guardar Cambios
        </button>
        <Deck></Deck>
      </div>
    </div>
);

};

export default EditDeck;
