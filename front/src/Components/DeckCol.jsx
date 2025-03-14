import { useEffect, useState } from "react";
import useDeckCards from "../Hooks/useDeckCards";
import { Link } from "react-router-dom";

const DeckCol = ({ deckId, deckName, deckCreationDate }) => {
    const { leaderImage } = useDeckCards(deckId);
    return (
        <tr className="text-center border-b hover:bg-gray-800">
            <td className="py-2 px-4 w-1/3">
                <Link to={`/decks/view/${deckId}`}>
                    <img
                        src={leaderImage || 'defaultImageUrl'}
                        alt="Leader"
                        className="h-15 w-12 mx-auto"
                    />
                </Link>
            </td>
            <td className="py-2 px-4 w-1/3">
                <Link to={`/decks/view/${deckId}`}>
                    {deckName}
                </Link>
            </td>
            <td className="py-2 px-4 w-1/3">
                <Link to={`/decks/view/${deckId}`}>
                    {new Date(deckCreationDate).toLocaleDateString()}
                </Link>
            </td>
        </tr>
    );
};

export default DeckCol;
