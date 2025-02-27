import { useState,useEffect } from 'react'
import Header from './Components/Header';
import CardList from './Components/CardList';

function App() {
  return (
    <>
    <Header/>  
    <CardList numPerPage={24} />
    </>
  );
}

export default App;