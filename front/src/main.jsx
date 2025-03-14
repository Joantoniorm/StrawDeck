import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';

import App from './App.jsx'
import CardList from './Components/CardList.jsx';
import EditDeck from './EditDeck.jsx';
import Login from './Components/Login.jsx';
import Register from './Components/RegisterForm.jsx';
import ProtectedRoute from './Components/ProtectedRoute.jsx';
import Header from './Components/Header.jsx';
import { AuthProvider } from './Hooks/AuthProvider.jsx';
import DeckBuilder from './DeckBuilder.jsx';
import ViewDeck from './ViewDeck.jsx';
import ViewAllDecks from './ViewAllDecks.jsx';


createRoot(document.getElementById('root')).render(
  <StrictMode>
    <AuthProvider>
      <Router basename="/">
        <Header />
        <Routes>
          <Route path='/' element={<App />}/>
          <Route path='/cardList' element={<CardList />} />
          <Route path='/login' element={<Login />} />
          <Route path='/register' element={<Register />} />
          <Route path='/decks/view/:deckId' element={<ViewDeck />} />
          <Route path='/decks' element={<ViewAllDecks />} />
          <Route
            path='/decks/edit/:deckId'
            element={
              <ProtectedRoute>
                <DeckBuilder />
              </ProtectedRoute>
            } />
          
        </Routes>
      </Router>
    </AuthProvider>
  </StrictMode>,
)
