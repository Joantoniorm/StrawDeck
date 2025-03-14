// src/Components/CreateDeckButton.jsx
import { useState } from "react";
import axios from "axios";
import Cookies from "js-cookie";
import { useAuth } from "../Hooks/AuthProvider";
import { useNavigate } from "react-router-dom";

const CreateDeckButton = () => {
    const [showCreateModal, setShowCreateModal] = useState(false);
    const [newDeckName, setNewDeckName] = useState("");
    const { isLogged, userId } = useAuth();
    const navigate = useNavigate();

    const openCreateModal = () => {
        if (!isLogged) {
            navigate("/login");
        } else {
            setShowCreateModal(true);
        }
    };

    const closeCreateModal = () => setShowCreateModal(false);

    const handleCreateDeck = async () => {
        if (!newDeckName.trim()) {
            alert("El nombre es obligatorio");
            return;
        }

        const token = Cookies.get("token") || localStorage.getItem("token");
        if (!token) {
            navigate("/login");
            return;
        }

        try {
            const response = await axios.post(
                "http://localhost:8080/decks/create",
                {
                    name: newDeckName,
                    activo: true,
                    user_id: userId,
                },
                {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                }
            );

            if (response.status === 200 || response.status === 201) {
                setNewDeckName("");
                closeCreateModal();
                navigate(`/decks/view/${response.data}`)
            }
        } catch (error) {
            console.error("Error al crear el mazo:", error);
        }
    };

    return (
        <div className="flex justify-center items-center">
            <button
                className="bg-green-500 min-h-[250px] min-w-[150px] text-white px-6 py-3 rounded-lg hover:bg-green-700 transition"
                onClick={openCreateModal}
            >
                Crear Nuevo Mazo
            </button>

            {showCreateModal && (
                <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center">
                    <div className="bg-white p-6 rounded shadow-lg w-80">
                        <h3 className="text-lg font-bold mb-4 text-black">Crear Nuevo Mazo</h3>
                        <input
                            type="text"
                            placeholder="Nombre del mazo"
                            value={newDeckName}
                            onChange={(e) => setNewDeckName(e.target.value)}
                            className="border p-2 w-full mb-4 text-black"
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

export default CreateDeckButton;
