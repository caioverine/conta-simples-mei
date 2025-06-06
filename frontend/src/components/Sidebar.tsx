import { NavLink, useNavigate } from "react-router-dom";
import { FaHome, FaSignOutAlt, FaDollarSign, FaArrowDown, FaTags } from "react-icons/fa";
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
            <NavLink
              to="/dashboard"
              className={({ isActive }) =>
                `flex items-center px-4 py-2 rounded transition-colors justify-center md:justify-start ${
                  isActive
                    ? "bg-[#234557] !text-white"
                    : "text-[#234557] hover:bg-gray-100"
                }`
              }
            >
              <FaHome size={20} />
              <span className="ml-3 hidden md:inline">Dashboard</span>
            </NavLink>
          </li>
          <li>
            <NavLink
              to="/receitas"
              className={({ isActive }) =>
                `flex items-center px-4 py-2 rounded transition-colors justify-center md:justify-start ${
                  isActive
                    ? "bg-[#234557] !text-white"
                    : "text-[#234557] hover:bg-gray-100"
                }`
              }
            >
              <FaDollarSign size={20} />
              <span className="ml-3 hidden md:inline">Receitas</span>
            </NavLink>
          </li>
          <li>
            <NavLink
              to="/despesas"
              className={({ isActive }) =>
                `flex items-center px-4 py-2 rounded transition-colors justify-center md:justify-start ${
                  isActive
                    ? "bg-[#234557] !text-white"
                    : "text-[#234557] hover:bg-gray-100"
                }`
              }
            >
              <FaArrowDown size={20} />
              <span className="ml-3 hidden md:inline">Despesas</span>
            </NavLink>
          </li>
          <li>
            <NavLink
              to="/categorias"
              className={({ isActive }) =>
                `flex items-center px-4 py-2 rounded transition-colors justify-center md:justify-start ${
                  isActive
                    ? "bg-[#234557] !text-white"
                    : "text-[#234557] hover:bg-gray-100"
                }`
              }
            >
              <FaTags size={20} />
              <span className="ml-3 hidden md:inline">Categorias</span>
            </NavLink>
          </li>
        </ul>
      </nav>
      <div className="p-4">
        <button
          className="sidebar-logout-btn flex items-center justify-center md:justify-start w-8 h-8 md:w-full md:h-auto rounded transition-colors bg-[#234557] text-white md:px-2 md:py-2"
          onClick={handleLogout}
          title="Sair"
        >
          <FaSignOutAlt size={24} className="text-white" />
          <span className="ml-3 hidden md:inline">Sair</span>
        </button>
      </div>
    </aside>
  );
}