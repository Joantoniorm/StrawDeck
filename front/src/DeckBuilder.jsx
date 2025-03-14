import React, { useState, useEffect } from "react";
import CardList from "./Components/CardList"; // Asegúrate de que el path sea correcto
import { useParams } from "react-router-dom";
import useDeckCards from "./Hooks/useDeckCards";
import Deck from "./Components/Deck";
import CardContainerDeck from "./Components/CardContainerDeck";
import axios from "axios";
import Cookies from "js-cookie"

const DeckBuilder = () => {
    const { deckId } = useParams();
    const { deckInfo, deckCards, originalDeckCards, setOriginalDeckCards} = useDeckCards(deckId);
    const [leaderImage, setLeaderImage] = useState('');
    const [newDeckName, setNewDeckName] = useState('');
    const [numberCards, setNumberCards] = useState();
    const [editedCards, setEditedCards] = useState({ Contains: [] });

    const characters = editedCards.Contains.filter(card => card.type === 'CHARACTER');
    const events = editedCards.Contains.filter(card => card.type === 'EVENT');
    const stages = editedCards.Contains.filter(card => card.type === 'STAGE');



    useEffect(() => {
        if (editedCards.Contains) {
            const totalCards = editedCards.Contains.reduce((total, card) => total + card.copies, 0);
            setNumberCards(totalCards);
    
            const leaderCard = editedCards.Contains.find(card => card.type === "LEADER");
            console.log("Carta de líder", leaderCard);
            setLeaderImage(leaderCard ? leaderCard.image_url : "");
        }
    }, [editedCards]);
    useEffect(() => {
        
    }, [leaderImage]);
    
    useEffect(() => {
        if (deckInfo?.name) {
            setNewDeckName(deckInfo.name);
            console.log(deckCards);
        }


        if (deckCards && deckCards.length > 0) {
            const totalCards = deckCards.reduce((total, card) => total + card.copies, 0);
            console.log(deckCards.length);
            setNumberCards(totalCards);
            setEditedCards({ Contains: deckCards });
            
            const leaderCard = deckCards.find(card => card.type === 'LEADER');
            setLeaderImage(leaderCard ? leaderCard.image : 'URL_DE_PLACEHOLDER');
        }
    }, [deckInfo, deckCards]);

    const addCard = (card) => {
        if (card.type === "LEADER") {
            const hasLeader = editedCards.Contains.some(c => c.type === "LEADER");
            if (hasLeader) {
                alert("Ya hay un líder en el deck. Solo puedes tener uno.");
                return;
            }
        }
    
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
    const removeLeaderCard = () => {
        setEditedCards((prev) => ({
            ...prev,
            Contains: prev.Contains.filter((c) => c.type !== 'LEADER'),
            
        })
        
    );
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
        const token = Cookies.get('token');
        try {
            if (toAdd.length > 0) {
                await axios.post(`http://localhost:8080/decks/${deckId}/addCards`, toAdd,
                    {
                        headers: {
                            Authorization: `Bearer ${token}`
                        }
                    }
                );
            }
            if (toUpdate.length > 0) {
                await axios.post(`http://localhost:8080/decks/${deckId}/updateCards`, toUpdate,
                    {
                        headers: {
                            Authorization: `Bearer ${token}`
                        }
                    });
            }
            if (toDelete.length > 0) {
                await axios.post(`http://localhost:8080/decks/${deckId}/deleteCards`, toDelete,
                    {
                        headers: {
                            Authorization: `Bearer ${token}`
                        }
                    });
            }

            console.log("Cambios guardados correctamente");
            setOriginalDeckCards(editedCards.Contains);
        } catch (error) {
            console.error("Error al guardar cambios:", error);
        }
    };
    return (
        <div className="w-full p-3 flex flex-col items-center justify-start">

            {/* Datos del Deck */}
            <div className="w-[70%] grid grid-cols-1 md:grid-cols-3 border-4 bg-gray-900 bg-black p-2 rounded-lg">

                <div className="p-5 flex justify-center items-center col-span-1 md:col-span-1">
                    <img src={leaderImage} alt={deckInfo.name} className="w-[193px] h-[265px] object-cover" />
                </div>


                <div className=" md:mt-10 md:ml-4 flex flex-col justify-center items-center md:justify-start md:items-start col-span-1 md:col-span-2">
                    <h1 className="">Deck:</h1>
                    <h1 className="text-4xl md:text-5xl font-extrabold text-white-600 uppercase tracking-wide shadow-lg">
                        {deckInfo.name}
                    </h1>
                    <h1 className="pt-4">{numberCards}/51</h1>
                </div>
            </div>

            {/* CardList */}
            <div className="w-full h-[60%] p-4">
                <CardList numPerPage={6} addCard={addCard} removeCard={removeCard} />
            </div>

            {/* Section del deck */}
            <div className="w-[90%]  grid grid-cols-1 md:grid-cols-3 border-4 bg-gray-900 bg-black p-2 rounded-lg">

                {/* Lider */}
                <div className="  text-white flex flex-col items-top justify-center">
                    <img src={leaderImage} alt={deckInfo.name} className="w-[193px] h-[265px] object-cover flex self-center" />
                    <div className="text-center mt-4">
                        <button
                            className="bg-red-500 text-white px-4 py-2 rounded hover:bg-blue-700"
                            onClick={removeLeaderCard}
                        >
                            Eliminar Leader
                        </button>

                    </div>
                    <div className="text-center mt-4">
                        <button
                            className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-700"
                            onClick={saveChanges}
                        >
                            Guardar Cambios
                        </button>

                    </div>
                </div>
                {/* EditedCards */}
                <div className=" md:mt-10 md:ml-4 flex flex-col justify-center items-center md:justify-start md:items-start col-span-1 md:col-span-2">
                    <div className="w-full h-auto p-4  border-5 border-black">
                        <p>Characters:</p>
                        <CardContainerDeck editedCards={characters} addCard={addCard} removeCard={removeCard} />
                    </div>
                    <div className="w-full h-auto p-4 border-5 border-black">
                        <p>Events:</p>
                        <CardContainerDeck editedCards={events} addCard={addCard} removeCard={removeCard} />
                    </div>
                    <div className="w-full h-auto p-4 border-5 border-black">
                        <p>Stages:</p>
                        <CardContainerDeck editedCards={stages} addCard={addCard} removeCard={removeCard} />
                    </div>
                </div>
            </div>
        </div>
    );
};

export default DeckBuilder;
