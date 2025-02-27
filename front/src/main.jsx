import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';

import App from './App.jsx'
import CardList from './Components/CardList.jsx';
import EditDeck from './EditDeck.jsx';

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <Router basename="/">
      <Routes>
        <Route path='/' element={<App/>}/>
        <Route path='/cardList' element={<CardList/>}/>
        <Route path='/decks/:deckId' element={<EditDeck/>}/>
      </Routes>
    </Router>
  </StrictMode>,
)
