import { useParams } from "react-router-dom";
import CardList from "./Components/CardList";
import axios from "axios";
import { useEffect, useState } from "react";
import useDeckCards from "./Hooks/useDeckCards";
import CardContainerDeck from "./Components/CardContainerDeck";
import Deck from "./Components/Deck";
import { useAuth } from "./Hooks/AuthProvider";
import Cookies from "js-cookie"

const EditDeck = () => {
    const { deckId } = useParams();
    const { deckCards, setDeckCards, originalDeckCards, setOriginalDeckCards } = useDeckCards(deckId);

    const [editedCards, setEditedCards] = useState({ Contains: [] });
    const [showCreateModal, setShowCreateModal] = useState(false);
    const [newDeckName, setNewDeckName] = useState("");
    const {isLogged,userId} = useAuth();
    
    useEffect(() => {
        setEditedCards({ Contains: deckCards });
    }, [deckCards]);
    const openCreateModal = () => setShowCreateModal(true);
    const closeCreateModal = () => setShowCreateModal(false);

    const handleCreateDeck = async () => {
        if (!newDeckName.trim()) {
            alert("El nombre es obligatorio");
            return;
        }
        if (!isLogged) {
            alert("Por favor inicia sesión para crear un mazo");
            return;
        }
        const token = Cookies.get("token") || localStorage.getItem("token");
        console.log("el token es:", token);
        if (!token) {
            alert("Token no encontrado, por favor inicia sesión nuevamente.");
            return;
        }
    
        try {
            const response = await axios.post(
                "http://localhost:8080/decks/create", 
                {
                    name: newDeckName,
                    activo: true,
                    user_id: userId
                },
                {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            );
    
            if (response.status === 200 || response.status === 201) {
                alert("Mazo creado correctamente");
                setNewDeckName("");
                closeCreateModal();
            }
        } catch (error) {
            console.error("Error al crear el mazo:", error);
            alert("Hubo un error al crear el mazo");
        }
    };

    return (
        <div className="p-4">
            <h2 className="text-2xl font-bold text-white text-center">Editar Mazo</h2>

            {/* Botón para crear nuevo mazo */}
            <div className="text-center mb-4">
                <button
                    className="bg-green-500 text-white px-4 py-2 rounded hover:bg-green-700"
                    onClick={openCreateModal}
                >
                    Crear Nuevo Mazo
                </button>
            </div>
            {/* Modal para crear mazo */}
            {showCreateModal && (
                <div className="fixed inset-0 bg-black bg-opacity-10 flex justify-center items-center">
                    <div className="bg-black p-6 rounded shadow-lg w-80">
                        <h3 className="text-lg font-bold mb-4">Crear Nuevo Mazo</h3>
                        <input
                            type="text"
                            placeholder="Nombre del mazo"
                            value={newDeckName}
                            onChange={(e) => setNewDeckName(e.target.value)}
                            className="border p-2 w-full mb-4"
                        />
                        <div className="flex justify-end gap-2">
                            <button
                                className="bg-gray-400 text-white px-3 py-1 rounded"
                                onClick={closeCreateModal}
                            >
                                Cancelar
                            </button>
                            <button
                                className="bg-green-500 text-white px-3 py-1 rounded hover:bg-green-700"
                                onClick={handleCreateDeck}
                            >
                                Crear
                            </button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
};

export default EditDeck;
