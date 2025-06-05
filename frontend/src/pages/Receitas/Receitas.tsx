import { useState } from "react";
import Navbar from "../../components/Navbar";
import { format } from "date-fns";
import { FaPlus, FaEdit, FaTrash } from "react-icons/fa";

// Mock de receitas
const mockReceitas = [
  { data: "2025-06-01", categoria: "Serviços", descricao: "Consultoria", valor: 1500 },
  { data: "2025-06-10", categoria: "Produtos", descricao: "Venda de produto", valor: 800 },
  { data: "2025-05-15", categoria: "Serviços", descricao: "Aula particular", valor: 600 },
  { data: "2025-05-20", categoria: "Outros", descricao: "Reembolso", valor: 200 },
];

const categorias = ["Todos", "Serviços", "Produtos", "Outros"];

const meses = [
  { value: "2025-06", label: "Junho/2025" },
  { value: "2025-05", label: "Maio/2025" },
];

const Receitas = () => {
  const [filtroMes, setFiltroMes] = useState("2025-06");
  const [filtroCategoria, setFiltroCategoria] = useState("Todos");

  const receitasFiltradas = mockReceitas.filter((r) => {
    const mes = r.data.slice(0, 7);
    const categoriaOk = filtroCategoria === "Todos" || r.categoria === filtroCategoria;
    return mes === filtroMes && categoriaOk;
  });

  return (
    <>
      <Navbar titulo="Receitas" />
      <div className="min-h-screen bg-gray-100">
        <div className="container mx-auto px-4 py-8">
          <div className="overflow-x-auto bg-white rounded-lg pt-2 pb-2 shadow-sm">
            <table className="min-w-full border-separate border-spacing-y-2">
              <thead>
                {/* Linha de filtros + botão */}
                <tr>
                  <th colSpan={5} className="pb-2 px-3 bg-gray-50 rounded-t-lg">
                    <div className="flex flex-col md:flex-row md:items-center md:justify-between gap-2">
                      <div className="flex gap-2 flex-wrap">
                        <select
                          className="rounded border-gray-300 px-3 py-2 text-sm"
                          value={filtroMes}
                          onChange={e => setFiltroMes(e.target.value)}
                        >
                          {meses.map(m => (
                            <option key={m.value} value={m.value}>{m.label}</option>
                          ))}
                        </select>
                        <select
                          className="rounded border-gray-300 px-3 py-2 text-sm"
                          value={filtroCategoria}
                          onChange={e => setFiltroCategoria(e.target.value)}
                        >
                          {categorias.map(c => (
                            <option key={c} value={c}>{c}</option>
                          ))}
                        </select>
                      </div>
                      <button className="flex items-center gap-2 bg-green-600 hover:bg-green-700 text-white px-4 py-2 rounded font-medium text-sm">
                        <FaPlus /> 
                      </button>
                    </div>
                  </th>
                </tr>
                {/* Cabeçalho da tabela */}
                <tr>
                  <th className="pb-2 px-3 text-gray-500 font-semibold bg-gray-50 rounded-l-lg text-center">Data</th>
                  <th className="pb-2 px-3 text-gray-500 font-semibold bg-gray-50 text-center">Categoria</th>
                  <th className="pb-2 px-3 text-gray-500 font-semibold bg-gray-50 text-center">Descrição</th>
                  <th className="pb-2 px-3 text-gray-500 font-semibold bg-gray-50 text-center">Valor</th>
                  <th className="pb-2 px-3 text-gray-500 font-semibold bg-gray-50 rounded-r-lg text-center">Ações</th>
                </tr>
              </thead>
              <tbody>
                {receitasFiltradas.length === 0 && (
                  <tr>
                    <td colSpan={5} className="text-center py-4 text-gray-400">Nenhuma receita encontrada.</td>
                  </tr>
                )}
                {receitasFiltradas.map((r, idx) => (
                  <tr
                    key={idx}
                    className="bg-gray-50 hover:bg-gray-100 transition rounded-lg shadow-sm"
                  >
                    <td className="py-2 px-3 rounded-l-lg font-mono text-sm text-center">{format(new Date(r.data), "dd/MM/yyyy")}</td>
                    <td className="py-2 px-3 text-center">{r.categoria}</td>
                    <td className="py-2 px-3 text-center">{r.descricao}</td>
                    <td className="py-2 px-3 font-semibold text-green-600 text-center">R$ {r.valor.toFixed(2)}</td>
                    <td className="py-2 px-3 rounded-r-lg flex justify-center gap-2">
                      <button className="p-2 rounded-full bg-blue-500 text-white hover:bg-blue-600" title="Editar">
                        <FaEdit size={14} />
                      </button>
                      <button className="p-2 rounded-full bg-red-500 text-white hover:bg-red-600" title="Excluir">
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

export default Receitas;