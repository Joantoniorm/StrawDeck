import { createContext, useContext, useState } from "react";
import Cookies from "js-cookie";
import { useNavigate } from "react-router-dom";
//Contexto
const AuthContext = createContext();

//Provider
export const AuthProvider = ({ children }) => {
    const storedToken = Cookies.get("token");
    const storedUserId = Cookies.get("user_id")
    
    const [isLogged, setIsLogged] = useState(!!storedToken);
    const [userId, setUserId] = useState(storedUserId)
    const [token, setToken] = useState(storedToken)

    const login = (token, newUserId) => {
        Cookies.set("token", token, {expires:7,secure:true, sameSite:'Strict'})
        Cookies.set('user_id', newUserId,{expires: 7, secure:true, sameSite:'Strict'});
        setUserId(newUserId);
        setToken(token);
        setIsLogged(true);
    }
    const logout = () => {
        Cookies.remove('token');
        Cookies.remove('user_id');
        Cookies.remove('JSESSIONID');
        setToken(null);
        setUserId(null);
        setIsLogged(false);
    }
    return (
        <AuthContext.Provider value={{ isLogged, login, logout, userId,token }}>
            {children}
        </AuthContext.Provider>
    );
};

//Hook
export const useAuth = () => {
    return useContext(AuthContext);
}