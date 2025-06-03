import { Link, useNavigate } from "react-router-dom";
import { FiHome, FiUser, FiLogOut } from "react-icons/fi";
import { useAuth } from "../contexts/AuthContext";
import logoGraficoSemFundo from '../assets/logo_grafico_sem_fundo.png';
import logoNomeSemFundo from '../assets/logo_nome_sem_fundo.png';

export default function Sidebar() {
  const { logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  return (
    <aside className="bg-white h-full transition-all duration-300 flex flex-col w-16 sm:w-16 md:w-56">
      <div className="p-4 flex items-center justify-center">
        {/* Colapsado: apenas logo, Expandido: nome */}
        <img
          src={logoGraficoSemFundo}
          alt="Logo"
          className="h-8 w-auto block md:hidden"
        />
        <img
          src={logoNomeSemFundo}
          alt="NomeLogo"
          className="h-6 w-auto hidden md:block"
        />
      </div>
      <nav className="flex-1">
        <ul className="space-y-2 mt-4">
          <li>
            <Link to="/dashboard" className="flex items-center px-4 py-2 rounded transition-colors justify-center md:justify-start">
              <FiHome size={20} />
              <span className="ml-3 hidden md:inline">Dashboard</span>
            </Link>
          </li>
          <li>
            <Link to="/perfil" className="flex items-center px-4 py-2 rounded transition-colors justify-center md:justify-start">
              <FiUser size={20} />
              <span className="ml-3 hidden md:inline">Perfil</span>
            </Link>
          </li>
        </ul>
      </nav>
      { /* TODO consertar botão sair colapsado */}
      <div className="p-4">
        <button
          className="flex items-center justify-center md:justify-start w-full rounded px-2 py-2 transition-colors bg-[#234557] text-white"
          onClick={handleLogout}
        >
          <FiLogOut size={20} className="text-white block" />
          <span className="ml-3 hidden md:inline">Sair</span>
        </button>
      </div>
    </aside>
  );
}