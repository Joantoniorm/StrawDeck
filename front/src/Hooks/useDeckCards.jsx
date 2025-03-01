import { useEffect, useState } from "react"
import axios from "axios";
const useDeckCards = (deckId)=>{
    //Info del deck
    const [deckInfo,setDeckInfo] = useState({});
    //Modificacion Local
    const [deckCards , setDeckCards]= useState([]);
    const [leaderImage, setLeaderImage]=useState(null);
    //Envio a Base de datos
    const [originalDeckCards, setOriginalDeckCards]= useState ([]);
    
    const fetchDeckInfo = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/decks/${deckId}`);
            setDeckInfo(response.data); 
        } catch (error) {
            console.error("Error cargando detalles del deck", error);
        }
    };
    const fetchDeckCards = async ()=> {
        try{
            const response = await axios.get(`http://localhost:8080/decks/${deckId}/cards`);
            const deckCards = response.data;
             const cardsWithDetails = await Promise.all (
                deckCards.map(async (deckCard)=>{
                    const cardResponse = await axios.get(`http://localhost:8080/cards/${deckCard.card_id}`);
                    return{
                        ...cardResponse.data,
                        copies:deckCard.quantity
                    };
                })
             );
            setDeckCards (cardsWithDetails);
            setOriginalDeckCards(cardsWithDetails);

            const leaderCard = cardsWithDetails.find(card=> card.type.toLowerCase () === "leader");
            setLeaderImage (leaderCard ? leaderCard.image_url : null ); 
        } catch (error){
            console.error("Error cargando Decks", error);
        }
    };
    useEffect(()=>{
        fetchDeckInfo
        fetchDeckCards();
    }, [deckId]);
    return{
        deckInfo,
        setDeckInfo,
        deckCards,
        setDeckCards,
        originalDeckCards,
        setOriginalDeckCards,
        leaderImage
    };
};
export default useDeckCards