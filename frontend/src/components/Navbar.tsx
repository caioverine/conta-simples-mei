import { useNavigate } from "react-router-dom";
import { useAuth } from "../contexts/AuthContext";

export default function Navbar() {
    const { logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/");
  };

  return (
    <header className="bg-white shadow p-4 flex justify-between items-center">
      <h1 className="text-xl font-semibold text-gray-800">Conta Simples MEI</h1>
      <div>
        {/* Aqui pode ir avatar, nome do usuário ou botão de logout */}
        <button className="text-sm text-blue-500 hover:underline" onClick={handleLogout}>Sair</button>
      </div>
    </header>
  );
}