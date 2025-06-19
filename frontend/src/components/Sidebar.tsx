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
    <aside 
      style={{ width: "var(--sidebar-width)" }}
      className="fixed top-0 left-0 h-screen z-30 bg-white dark:bg-gray-900 transition-all duration-300 flex flex-col">
      <div className="p-4 flex items-center justify-center">
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
                    : "text-[#234557] dark:text-gray-200 hover:bg-gray-100 dark:hover:bg-gray-800"
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
                    : "text-[#234557] dark:text-gray-200 hover:bg-gray-100 dark:hover:bg-gray-800"
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
                    : "text-[#234557] dark:text-gray-200 hover:bg-gray-100 dark:hover:bg-gray-800"
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
                    : "text-[#234557] dark:text-gray-200 hover:bg-gray-100 dark:hover:bg-gray-800"
                }`
              }
            >
              <FaTags size={20} />
              <span className="ml-3 hidden md:inline">Categorias</span>
            </NavLink>
          </li>
        </ul>
      </nav>
      <div className="p-0 flex mt-auto">
        <button
          className="sidebar-logout-btn flex items-center justify-center md:justify-start w-full h-8 md:h-auto rounded transition-colors bg-[#234557] text-white md:px-2 md:py-2 dark:bg-gray-800 dark:text-gray-200"
          onClick={handleLogout}
          title="Sair"
        >
          <FaSignOutAlt size={24} className="text-white dark:text-gray-200" />
          <span className="ml-3 hidden md:inline">Sair</span>
        </button>
      </div>
    </aside>
  );
}