import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { FiMenu, FiHome, FiUser, FiLogOut } from "react-icons/fi";
import { useAuth } from "../contexts/AuthContext";

export default function Sidebar() {
  const [collapsed, setCollapsed] = useState(false);
  const { logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  return (
    <aside className="bg-white h-full transition-all duration-300 border-r border-gray-200 ${collapsed ? 'w-16' : 'w-56'} flex flex-col">
      <button
        className="p-4 focus:outline-none !bg-white !border-transparent transition-colors"
        onClick={() => setCollapsed((prev) => !prev)}
        aria-label="Alternar sidebar"
      >
        <FiMenu size={24} />
      </button>
      <nav className="flex-1">
        <ul className="space-y-2 mt-4">
          <li>
            <Link to="/dashboard" className="flex items-center px-4 py-2 rounded transition-colors">
              <FiHome size={20} />
              {!collapsed && <span className="ml-3">Dashboard</span>}
            </Link>
          </li>
          <li>
            <Link to="/perfil" className="flex items-center px-4 py-2  rounded transition-colors">
              <FiUser size={20} />
              {!collapsed && <span className="ml-3">Perfil</span>}
            </Link>
          </li>
        </ul>
      </nav>
      <div className="p-4">
        <button
          className="flex items-center w-full rounded px-2 py-2 transition-colors text-white"
          onClick={handleLogout}
        >
          <FiLogOut size={20} className="text-white" />
          {!collapsed && <span className="ml-3">Sair</span>}
        </button>
      </div>
    </aside>
  );
}