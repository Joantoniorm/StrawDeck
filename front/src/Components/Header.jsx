const Header = ()=>{
    return (
        <header className="bg-gray-900 hover:bg-red-800 text-white p-4 shadow-md w-full transition-colors">
          <div className="container mx-auto flex justify-between items-center">
            {/* Logo */}
            <div className="text-xl font-bold">MyLogo</div>
    
            {/* Botones */}
            <div className="flex space-x-4">
              <button className="px-4 py-2 bg-blue-600 hover:bg-blue-700 hover:shadow-lg border border-white rounded-lg transition">
                Home
              </button>
              <button className="px-4 py-2 bg-green-600 hover:bg-red-700 hover:shadow-lg border border-white rounded-lg transition">
                About
              </button>
              <button className="px-4 py-2 bg-red-600 hover:bg-red-700 hover:shadow-lg border border-white rounded-lg transition">
                Contact
              </button>
            </div>
          </div>
        </header>
      );
    };
export default Header;
