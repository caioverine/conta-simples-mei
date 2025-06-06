import { useState } from "react";
import Navbar from "../../components/Navbar";
import { FaPlus, FaEdit, FaTrash } from "react-icons/fa";

// Mock de categorias
const mockCategorias = [
  { nome: "Fornecedores" },
  { nome: "Operacional" },
  { nome: "Materiais" },
  { nome: "Outros" },
];

const Categorias = () => {
  const [categorias] = useState(mockCategorias);
  const [filtroNome, setFiltroNome] = useState("");

  const categoriasFiltradas = categorias.filter((cat) =>
    cat.nome.toLowerCase().includes(filtroNome.toLowerCase())
  );

  return (
    <>
      <Navbar titulo="Categorias" />
      <div className="min-h-screen bg-gray-100">
        <div className="container mx-auto px-4 py-8">
          <div className="overflow-x-auto bg-white rounded-lg pt-2 pb-2 shadow-sm">
            <table className="min-w-full border-separate border-spacing-y-2">
              <thead>
                {/* Linha de filtro + botão */}
                <tr>
                  <th colSpan={2} className="pb-2 px-3 bg-gray-50 rounded-t-lg">
                    <div className="flex flex-col md:flex-row md:items-center md:justify-between gap-2">
                      <input
                        type="text"
                        placeholder="Filtrar por nome..."
                        className="rounded border-gray-300 px-3 py-2 text-sm w-full md:w-64"
                        value={filtroNome}
                        onChange={(e) => setFiltroNome(e.target.value)}
                      />
                      <button className="flex items-center gap-2 bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded font-medium text-sm mt-2 md:mt-0">
                        <FaPlus />
                      </button>
                    </div>
                  </th>
                </tr>
                {/* Cabeçalho da tabela */}
                <tr>
                  <th className="pb-2 px-3 text-gray-500 font-semibold bg-gray-50 rounded-l-lg text-center">
                    Nome
                  </th>
                  <th className="pb-2 px-3 text-gray-500 font-semibold bg-gray-50 rounded-r-lg text-center">
                    Ações
                  </th>
                </tr>
              </thead>
              <tbody>
                {categoriasFiltradas.length === 0 && (
                  <tr>
                    <td
                      colSpan={2}
                      className="text-center py-4 text-gray-400"
                    >
                      Nenhuma categoria encontrada.
                    </td>
                  </tr>
                )}
                {categoriasFiltradas.map((cat, idx) => (
                  <tr
                    key={idx}
                    className="bg-gray-50 hover:bg-gray-100 transition rounded-lg shadow-sm"
                  >
                    <td className="py-2 px-3 rounded-l-lg text-center">
                      {cat.nome}
                    </td>
                    <td className="py-2 px-3 rounded-r-lg flex justify-center gap-2">
                      <button
                        className="p-2 rounded-full bg-blue-500 text-white hover:bg-blue-600"
                        title="Editar"
                      >
                        <FaEdit size={14} />
                      </button>
                      <button
                        className="p-2 rounded-full bg-red-500 text-white hover:bg-red-600"
                        title="Excluir"
                      >
                        <FaTrash size={14} />
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </>
  );
};

export default Categorias;