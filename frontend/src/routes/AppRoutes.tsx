import { Navigate, Route, Routes } from "react-router-dom";
import Login from "../pages/Login";
import Dashboard from "../pages/Dashboard/Dashboard";
import Layout from "../components/Layout";
import Receitas from "../pages/Receitas/Receitas";
import CadastroUsuario from "../pages/CadastroUsuario";
import Despesas from "../pages/Despesas/Despesas";
import Categorias from "../pages/Categorias/Categorias";
import { useAuth } from "../contexts/useAuth";

export default function AppRoutes() {
  const { isAuthenticated, loadingAuth } = useAuth();

  if (loadingAuth) {
    return <div>Carregando...</div>;
  }

  return (
    <>
      <Routes>
        <Route path="/login" element={<Login />}/>
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
                  <Route 
                    path="/despesas" 
                    element={isAuthenticated ? <Despesas /> : <Navigate to="/login" />}
                  />
                  <Route 
                    path="/categorias" 
                    element={isAuthenticated ? <Categorias /> : <Navigate to="/login" />}
                  />
                  <Route path="*" element={<Navigate to="/dashboard" replace />} />
                </Routes>
              </Layout>
            }
          />
      </Routes>
    </>
  );
}