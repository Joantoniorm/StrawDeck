import React, { useEffect, useState } from 'react';
import dictionary from '../assets/Diccionario.json'
const CardFilter = ({  cards, setFilteredCards }) => {

    const [filters, setFilters] = useState({
        name:'',
        color:'',
        type:'',
    });
    useEffect(()=>{
        let filtered = cards;
        if(filters.name){
            filtered= filtered.filter((card)=>
            card.name.toLowerCase().includes(filters.name.toLowerCase()));
        }
        if(filters.color){
            filtered = filtered.filter((card)=>
            card.color.toLowerCase() === filters.color.toLowerCase());
        }
        if(filters.type){
            filtered = filtered.filter((card)=>
            card.type.toLowerCase()=== filters.type.toLowerCase());
        }
        setFilteredCards(filtered);
    },[filters,cards,setFilteredCards]);
    
    const handleFilterChange = (e)=>{
        setFilters({...filters, [e.target.name]: e.target.value});
    }

    return (
        <div className='flex flex-col items-center p-4'>
          <input
            type='text'
            name='name'
            placeholder='Buscar carta...'
            value={filters.name}
            onChange={handleFilterChange}
            className='p-2 border rounded-md w-1/2 mb-2'
          />
          <select
            name='color'
            onChange={handleFilterChange}
            value={filters.color}
            className='p-2 border rounded-md w-1/2 mb-2'
          >
            <option value=''>Todos los colores</option>
            {dictionary["One Piece"].Colors.map((color) => (
              <option key={color} value={color}>{color}</option>
            ))}
          </select>
          <select
            name='type'
            onChange={handleFilterChange}
            value={filters.type}
            className='p-2 border rounded-md w-1/2 mb-2'
          >
            <option value=''>Todos los tipos</option>
            {dictionary["One Piece"].Type.map((type) => (
              <option key={type} value={type}>{type}</option>
            ))}
          </select>
        </div>
      );
};

export default CardFilter;
