import { Link } from "react-router-dom";

export default function Sidebar() {
  return (
    <aside className="flex flex-col w-64 h-screen px-5 py-8 overflow-y-auto bg-white border-r rtl:border-r-0 rtl:border-l dark:bg-gray-900 dark:border-gray-700">
      <div className="w-auto h-7">
        Menu
      </div>
      <div className="flex flex-col justify-between flex-1 mt-6">
        <nav className="-mx-3 space-y-6 ">
          <div className="space-y-3 ">
            <label className="px-3 text-xs text-gray-500 uppercase dark:text-gray-400">analytics</label>
            
            <Link to="/dashboard" className="flex items-center px-3 py-2 text-gray-600 transition-colors duration-300 transform rounded-lg dark:text-gray-200 hover:bg-gray-100 dark:hover:bg-gray-800 dark:hover:text-gray-200 hover:text-gray-700">
              <span className="mx-2 text-sm font-medium">Dashboard</span>
            </Link>
          
            <Link to="/receitas" className="flex items-center px-3 py-2 text-gray-600 transition-colors duration-300 transform rounded-lg dark:text-gray-200 hover:bg-gray-100 dark:hover:bg-gray-800 dark:hover:text-gray-200 hover:text-gray-700">
              <span className="mx-2 text-sm font-medium">Receitas</span>
            </Link>

            <Link to="/despesas" className="flex items-center px-3 py-2 text-gray-600 transition-colors duration-300 transform rounded-lg dark:text-gray-200 hover:bg-gray-100 dark:hover:bg-gray-800 dark:hover:text-gray-200 hover:text-gray-700">
              <span className="mx-2 text-sm font-medium">Despesas</span>
            </Link>
          </div>
        </nav>
      </div>
    </aside>
  );
}