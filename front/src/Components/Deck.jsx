import { useEffect, useState } from "react"
import { useParams } from "react-router-dom"
import useDeckCards from "../Hooks/useDeckCards";

const Deck =()=>{
    const {deckId} = useParams();
    const {deckCards, leaderImage,deckInfo} = useDeckCards(deckId);
    return (
        <>
        <div 
        className="relative cursor-pointer border border-gray-300 p-4 rounded-lg shadow-md bg-gray-800 text-white w-[150px] hover:scale-105 transition-transform"> 
        </div>
        DECK
        <img src={leaderImage} alt={deckInfo.name} className="w-full h-auto rounded-md" />
        </>
    )
}
export default Deck;