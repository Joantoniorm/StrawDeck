import axios from "axios";
import { useEffect, useState } from "react"
import Deck from "./Deck";
import CreateDeckButton from "./CreateDeckButton";

const DeckList = () => {
    const [AllDecks, setAllDecks] = useState([]);
    useEffect(() => {
        axios.get("http://localhost:8080/decks/all").then((response) => {
            setAllDecks(response.data);
        }).catch((error)=>{
            console.error("Error cargando decks",error);
        })
    },[])
    return(
        <div className="p-4">
        <h1 className="text-3xl font-bold text-center mb-6">DeckList</h1>
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-3 gap-6">
            {AllDecks.map((deck) => (
                <div 
                    key={deck.id} 
                    className="flex justify-center items-center p-4"
                >
                    <Deck deckId={deck.id} className="w-full h-full max-w-[300px] max-h-[400px] flex items-center justify-center bg-gray-100 shadow-lg rounded-lg" />
                </div>
            ))}
            <CreateDeckButton/>
        </div>
        
    </div>
    )
}
export default DeckList;