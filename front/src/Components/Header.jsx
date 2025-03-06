import { useNavigate } from "react-router-dom";
import { useAuth } from "../Hooks/AuthProvider";
import { Link } from "react-router-dom";

const Header = () => {
  const navigate = useNavigate();

  const { isLogged, logout } = useAuth();
  const handleLogout = ()=>{
    logout();
    navigate('/');
  }


  return (
    <header className="w-full bg-indigo-600 shadow-md">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between items-center h-16">
          <div className="flex-shrink-0">
            <Link to="/" className="text-xl font-bold text-white">
              StrawDecks
            </Link>
          </div>
          <div className="flex space-x-4">
            <Link to="/cardList" className="px-4 py-2 rounded bg-blue-500 text-white hover:bg-blue-700 transition">
              Cards
            </Link>
            <Link to="/decks" className="px-4 py-2 rounded bg-blue-500 text-white hover:bg-blue-700 transition">
              Decks
            </Link>
            {isLogged ? (
              <button onClick={handleLogout} className="px-4 py-2 rounded bg-red-500 text-white hover:bg-red-700 transition">Logout</button>
            ) : (
              <Link to="/login" className="px-4 py-2 rounded bg-green-500 text-white hover:bg-green-700 transition">
                Login
              </Link>
            )}
          </div>
        </div>
      </div>
    </header>
  );
};

export default Header;