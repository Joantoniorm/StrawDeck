import { useEffect, useState } from "react"
import { useLocation, useParams, Link } from "react-router-dom"
import useDeckCards from "../Hooks/useDeckCards";

const Deck = ({ deckId: propDeckId }) => {
    const { deckId: paramsDeckId } = useParams();
    const deckId = propDeckId || paramsDeckId;
    const location = useLocation();
    const { deckCards, leaderImage, deckInfo } = useDeckCards(deckId);
    return (
        <>
            <Link to={`/decks/view/${deckId}`} className="relative cursor-pointer border border-gray-300 p-4 rounded-lg shadow-md bg-gray-800 text-white w-[60%] hover:scale-105 transition-transform">
            
            <div className="w-full h-[250px] flex justify-center items-center bg-gray-600 rounded-md">
                {leaderImage ? (
                    <img src={leaderImage} alt={deckInfo.name} className="w-full h-full object-cover rounded-md" />
                ) : (
                    <span className="text-white text-lg">Sin Imagen</span> 
                )}
            </div>

            <div className="absolute bottom-1 inset-x-4 bg-black/90 text-white text-center py-1">
                {deckInfo?.name || "Sin nombre"}
            </div>
        </Link>

        </>
    )
}
export default Deck;