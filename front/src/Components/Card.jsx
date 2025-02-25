import { useState } from "react";

const Card = ({ card }) => {
  const [showDetails, setShowDetails] = useState(false);

  return (
    <div onClick={() => setShowDetails(!showDetails)} style={{ cursor: "pointer", border: "1px solid #ccc", padding: "10px", borderRadius: "8px", textAlign: "center", width: "150px" }}>
      <img src={card.image_url} alt={card.name} style={{ width: "100%", height: "auto" }} />
      {showDetails && (
        <div>
          <h3>{card.name}</h3>
          <p><strong>Ataque:</strong> {card.power}</p>
          <p><strong>Defensa:</strong> {card.defense}</p>
          <p><strong>Tipo:</strong> {card.type}</p>
        </div>
      )}
    </div>
  );
};

export default Card;
