import React, { useState, useEffect } from "react";
import { useParams, Link } from "react-router-dom";
import useDeckCards from "./Hooks/useDeckCards";
import CardContainerDeck from "./Components/CardContainerDeck";


const ViewDeck = () => {
    const { deckId } = useParams();
    const { deckInfo, deckCards, originalDeckCards, setOriginalDeckCards, leaderImage } = useDeckCards(deckId);

    const [numberCards, setNumberCards] = useState();
    const [editedCards, setEditedCards] = useState({ Contains: [] });

    const characters = deckCards.filter(card => card.type === 'CHARACTER');
    const events = deckCards.filter(card => card.type === 'EVENT');
    const stages = deckCards.filter(card => card.type === 'STAGE');
    useEffect(() => {
        const totalCards = deckCards.reduce((total, card) => total + card.copies, 0);
        setNumberCards(totalCards)
    })
    return (
        <div className="w-full p-3 flex flex-col items-center justify-start">
            <div className="w-[70%] grid grid-cols-1 md:grid-cols-3 border-4 bg-gray-900 bg-black p-2 rounded-lg">
                <div className="p-5 flex justify-center items-center col-span-1 md:col-span-1">
                    <img src={leaderImage} alt={deckInfo.name} className="w-[193px] h-[265px] object-cover" />
                </div>
                <div className="md:mt-10 md:ml-4 flex flex-col justify-center items-center md:justify-start md:items-start col-span-1 md:col-span-2">
                    <h1 className="text-xl md:text-2xl font-semibold text-white">Deck:</h1>
                    <h1 className="text-4xl md:text-5xl font-extrabold text-white-600 uppercase tracking-wide shadow-lg">
                        {deckInfo.name}
                    </h1>
                    <h1 className="pt-4">Total Cards: {numberCards}/51</h1>
                    <div className="text-center mt-4">
                        <Link to={`/decks/edit/${deckId}`}>
                            <button className="bg-green-500 text-white px-4 py-2 mt-5 rounded hover:bg-blue-700">
                                Editar
                            </button>
                        </Link>
                    </div>
                </div>
            </div>
            <div className="w-[100%] min-h-[650px]  grid grid-cols-1 md:grid-cols-3 border-4 bg-gray-900 bg-black p-2 rounded-lg">
                <div className="text-white flex flex-col items-start justify-start h-[300px]">
                    <img src={leaderImage} alt={deckInfo.name} className="w-[400px] h-[520px] mt-10 self-center" />
                </div>
                <div className=" md:mt-10 md:ml-4 flex flex-col justify-center items-center md:justify-start md:items-start col-span-1 md:col-span-2">
                    <div className="w-full h-auto p-4  border-5 border-black">
                        <p>Characters:</p>
                        <CardContainerDeck editedCards={characters} />
                    </div>
                    <div className="w-full h-auto p-4 border-5 border-black">
                        <p>Events:</p>
                        <CardContainerDeck editedCards={events} />
                    </div>
                    <div className="w-full h-auto p-4 border-5 border-black">
                        <p>Stages:</p>
                        <CardContainerDeck editedCards={stages} />
                    </div>
                </div>
            </div>
        </div>
    );
}
export default ViewDeck