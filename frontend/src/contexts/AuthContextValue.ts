import { createContext } from "react";

export interface AuthContextType {
  token: string | null;
  isAuthenticated: boolean;
  loadingAuth: boolean;
  login: (token: string) => void;
  logout: () => void;
}

export const AuthContext = createContext<AuthContextType | undefined>(undefined);