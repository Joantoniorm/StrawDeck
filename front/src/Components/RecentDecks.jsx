import { useEffect, useState } from "react";
import axios from "axios";
import DeckCol from "./DeckCol";


const RecentDecks = () => {
    const [recentDecks, setRecentDecks] = useState([]);

    // Obtener los últimos decks
    useEffect(() => {
        axios.get("http://localhost:8080/decks/lastDecks")
            .then((response) => {
                setRecentDecks(response.data);
            })
            .catch((error) => {
                console.error("Error cargando decks", error);
            });
    }, []);

    return (
        <div className="max-w-4xl mx-auto mt-10">
            <h2 className="text-xl font-bold mb-4 text-center">Últimos Decks</h2>
            <div className="overflow-x-auto">
                <table className="min-w-full border border-black-600 shadow-md rounded-lg">
                    <thead>
                        <tr className="bg-blue-600 text-white">
                            <th className="py-2 px-4 border-b w-1/3">Leader</th>
                            <th className="py-2 px-4 border-b w-1/3">Nombre del Mazo</th>
                            <th className="py-2 px-4 border-b w-1/3">Fecha de Creación</th>
                        </tr>
                    </thead>
                    <tbody>
                        {recentDecks.map((deck) => (
                            <DeckCol
                                key={deck.id} 
                                deckId={deck.id} 
                                deckName={deck.name} 
                                deckCreationDate={deck.dateofcreation} 
                            />
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default RecentDecks;
