import React from "react"
import Card from "./Card"
const DeckView =({editedCards, addCard, removeCard})=>{
    console.log("editedCards:", editedCards)
    return (
        <div className="p-4">
            <div className="grid grid-cols-2 gap-4">
                {editedCards.Contains.map((card)=>(
                    <Card key={card.id} card={card} addCard={() => addCard(card)}  removeCard={() => removeCard(card.id)} />
                ))}
            </div>
        </div>
    )
}
export default DeckView