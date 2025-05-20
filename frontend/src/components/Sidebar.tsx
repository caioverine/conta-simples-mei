import { Link } from "react-router-dom";

export default function Sidebar() {
  return (
    <aside className="w-64 bg-gray-800 text-white h-full flex flex-col">
      <div className="p-4 text-lg font-bold border-b border-gray-700">
        Menu
      </div>
      <nav className="flex-1 p-4 space-y-2">
        <Link to="/dashboard" className="block hover:bg-gray-700 p-2 rounded">Dashboard</Link>
        <Link to="/receitas" className="block hover:bg-gray-700 p-2 rounded">Receitas</Link>
        <Link to="/despesas" className="block hover:bg-gray-700 p-2 rounded">Despesas</Link>
      </nav>
    </aside>
  );
}