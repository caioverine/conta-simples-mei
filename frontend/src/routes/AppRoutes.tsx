import { Navigate, Route, Routes } from "react-router-dom";
import Login from "../pages/Login";
import Dashboard from "../pages/Dashboard";
import { useAuth } from "../contexts/AuthContext";
import Layout from "../components/Layout";
import Receitas from "../pages/Receitas";
import CadastroUsuario from "../pages/CadastroUsuario";

export default function AppRoutes() {
  const { isAuthenticated } = useAuth();

  return (
    <>
      <Routes>
        <Route path="/login" element={
          <Login />
          }
        />
        <Route path="/cadastro" element={<CadastroUsuario />} />

        <Route
            path="/*"
            element={
              <Layout>
                <Routes>
                  <Route
                    path="/dashboard"
                    element={isAuthenticated ? <Dashboard /> : <Navigate to="/login" />}
                  />
                  <Route 
                    path="/receitas" 
                    element={isAuthenticated ? <Receitas /> : <Navigate to="/login" />}
                  />
                  {/* Aqui você pode adicionar outras rotas protegidas */}
                </Routes>
              </Layout>
            }
          />
      </Routes>
    </>
  );
}