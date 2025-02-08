import React, { useEffect, useState } from 'react';

const CardList = () => {
    const [cards, setCards] = useState([]); 
    const [displayedCards, setDisplayedCards] = useState([]); 
    const [page, setPage] = useState(1); 
    const cardsPerPage = 30;

    useEffect(() => {
        fetch('/cardData.json')
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Error al obtener los datos');
                }
                return response.json();
            })
            .then((data) => {
                if (data && data.length) {
                    setCards(data);
                    setDisplayedCards(data.slice(0, cardsPerPage)); // Solo las primeras 30 cartas
                } else {
                    console.error('Datos de cartas no válidos:', data);
                }
            })
            .catch((error) => {
                console.error('Error al cargar las cartas:', error);
            });
    }, []);

    const loadMoreCards = () => {
        const nextPage = page + 1;
        const startIndex = nextPage * cardsPerPage;
        const newDisplayedCards = cards.slice(startIndex, startIndex + cardsPerPage);
        setDisplayedCards((prevCards) => [...prevCards, ...newDisplayedCards]);
        setPage(nextPage);
    };

    return (
        <div>
            <h1>Lista de Cartas</h1>
            <div style={{ display: 'flex', flexWrap: 'wrap', gap: '16px' }}>
                {displayedCards.map((card, index) => (
                    <div
                        key={index}
                        style={{
                            border: '1px solid #ccc',
                            borderRadius: '8px',
                            padding: '16px',
                            width: '200px',
                        }}
                    >
                        
                        <h3>{card.name}</h3>
                        <p><strong>Cost:</strong> {card.cost}</p>
                        <p><strong>Power:</strong> {card.power}</p>
                        <p><strong>Counter:</strong> {card.counter}</p>
                        <p><strong>Color:</strong> {card.color}</p>
                        <p><strong>Type:</strong> {card.type}</p>
                        <p><strong>Effect:</strong> {card.effect}</p>
                        <p><strong>Set:</strong> {card.number}</p>
                    </div>
                ))}
            </div>
            <button
                onClick={loadMoreCards}
                style={{
                    display: 'block',
                    margin: '20px auto',
                    padding: '10px 20px',
                    fontSize: '16px',
                    cursor: 'pointer',
                }}
            >
                Cargar más cartas
            </button>
        </div>
    );
};

export default CardList;
