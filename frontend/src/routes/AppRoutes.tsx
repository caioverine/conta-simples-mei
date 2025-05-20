import { Navigate, Route, Routes } from "react-router-dom";
import Login from "../pages/Login";
import Dashboard from "../pages/Dashboard";
import { useAuth } from "../contexts/AuthContext";
import Layout from "../components/Layout";

export default function AppRoutes() {
  const { isAuthenticated } = useAuth();

  return (
    <Routes>
      <Route path="/" element={<Login />} />

      <Route
          path="/*"
          element={
            <Layout>
              <Routes>
                <Route
                  path="/dashboard"
                  element={isAuthenticated ? <Dashboard /> : <Navigate to="/" />}
                />
                {/* Aqui você pode adicionar outras rotas protegidas */}
              </Routes>
            </Layout>
          }
        />
    </Routes>
  );
}