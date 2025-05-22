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
    <nav className="container p-6 mx-auto lg:flex lg:justify-between lg:items-center">
      <div className="flex items-center justify-between">
        Conta Simples MEI
      </div>
      <div className="flex">
        <button className="text-gray-500 dark:text-gray-200 hover:text-gray-600 dark:hover:text-gray-400 focus:outline-none focus:text-gray-600 dark:focus:text-gray-400" 
          onClick={handleLogout}>
            Sair
        </button>
      </div>
    </nav>
  );
}