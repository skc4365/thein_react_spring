import { createContext, useContext, useState } from 'react';
import api from '../api/axios';

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);
  const [accessToken, setAccessToken] = useState(null);

  const login = async (username, password) => {
    const res = await api.post('/auth/login', { username, password });
    setAccessToken(res.data.accessToken);
    window.__accessToken = res.data.accessToken;
    setUser({ username: res.data.username, name: res.data.name, role: res.data.role });
  };

  const logout = () => {
    setAccessToken(null);
    window.__accessToken = null;
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ user, accessToken, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}

export const useAuth = () => useContext(AuthContext);