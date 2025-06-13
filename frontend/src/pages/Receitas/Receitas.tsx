import { useState, useEffect } from "react";
import Navbar from "../../components/Navbar";
import { format, subMonths } from "date-fns";
import { FaPlus, FaEdit, FaTrash } from "react-icons/fa";
import { listarReceitas, salvarReceita } from "../../services/receita-service";
import { listarCategoriasReceita } from "../../services/categoria-service";
import type { Receita } from "../../model/receita-model";
import Pagination from "../../components/Pagination";
import ModalNovaReceita, { type ReceitaFormData } from "./ModalNovaReceita";
import { ModalSucesso } from "../../components/ModalSucesso";
import { ptBR } from "date-fns/locale";

const meses = Array.from({ length: 6 }).map((_, i) => {
  const date = subMonths(new Date(), i);
  return {
    value: format(date, "yyyy-MM"),
    label: format(date, "MMMM/yyyy", { locale: ptBR })
      .replace(/^./, (str) => str.toUpperCase()),
  };
});

const Receitas = () => {
  const [filtroMes, setFiltroMes] = useState(meses[0].value);
  const [filtroCategoria, setFiltroCategoria] = useState("Todos");
  const [receitas, setReceitas] = useState<Receita[]>([]);
  const [categorias, setCategorias] = useState<string[]>(["Todos"]);
  const [loading, setLoading] = useState(false);
  const [showModal, setShowModal] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [success, setSuccess] = useState<string | null>(null);

  const [page, setPage] = useState(0);
  const [size] = useState(5);
  const [totalPages, setTotalPages] = useState(1);

  useEffect(() => {
    async function fetchReceitas() {
      setLoading(true);
      try {
        const resp = await listarReceitas(page, size);
        setReceitas(resp.data.content);
        setTotalPages(resp.data.totalPages);
      } catch (err) {
        console.error("Erro ao carregar receitas:", err);
        setError("Erro ao carregar receitas. Tente novamente.");
        setLoading(false);
      } finally {
        setLoading(false);
      }
    }
    fetchReceitas();
  }, [page, size]);

  useEffect(() => {
    async function fetchCategorias() {
      try {
        const resp = await listarCategoriasReceita();
        // Adiciona "Todos" no início da lista
        setCategorias(["Todos", ...resp.data.map((cat) => cat.nome)]);
      } catch (err) {
        console.error("Erro ao carregar categorias:", err);
        setError("Erro ao carregar categorias. Tente novamente.");
        setCategorias(["Todos"]);
      }
    }
    fetchCategorias();
  }, []);

  const receitasFiltradas = receitas.filter((r) => {
    const mes = r.data.slice(0, 7);
    const categoriaOk = filtroCategoria === "Todos" || r.categoria.nome === filtroCategoria;
    return mes === filtroMes && categoriaOk;
  });

  // Função para salvar nova receita
  const handleSaveReceita = async (data: ReceitaFormData) => {
    setLoading(true);
    setError(null);
    try {
      await salvarReceita(data);
      // Recarrega a lista após salvar
      const resp = await listarReceitas(page, size);
      setReceitas(resp.data.content);
      setTotalPages(resp.data.totalPages);
      setShowModal(false);
      setSuccess("Receita cadastrada com sucesso!");
    } catch (err: unknown) {
      console.error("Erro ao salvar receita:", err);
      setError("Erro ao salvar receita. Tente novamente.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <>
      <Navbar titulo="Receitas" />
      <div className="min-h-screen bg-gray-100 dark:bg-gray-900">
        <div className="container mx-auto px-4 py-8">
          <div className="overflow-x-auto bg-white dark:bg-gray-800 rounded-lg pt-2 pb-2 shadow-sm">
            <table className="min-w-full border-separate border-spacing-y-2">
              <thead>
                {/* Linha de filtros + botão */}
                <tr>
                  <th colSpan={5} className="pb-2 px-3 bg-gray-50 dark:bg-gray-900 rounded-t-lg">
                    <div className="flex flex-col md:flex-row md:items-center md:justify-between gap-2 mt-2">
                      <div className="flex gap-2 flex-wrap">
                        <select
                          className="rounded border-gray-300 dark:border-gray-700 px-3 py-2 text-sm bg-white dark:bg-gray-900 text-gray-700 dark:text-gray-300"
                          value={filtroMes}
                          onChange={e => setFiltroMes(e.target.value)}
                        >
                          {meses.map(m => (
                            <option key={m.value} value={m.value}>{m.label}</option>
                          ))}
                        </select>
                        <select
                          className="rounded border-gray-300 dark:border-gray-700 px-3 py-2 text-sm bg-white dark:bg-gray-900 text-gray-700 dark:text-gray-300"
                          value={filtroCategoria}
                          onChange={e => setFiltroCategoria(e.target.value)}
                        >
                          {categorias.map(c => (
                            <option key={c} value={c}>{c}</option>
                          ))}
                        </select>
                      </div>
                      <button
                        className="btn-icn flex items-center gap-2 font-medium"
                        onClick={() => setShowModal(true)}
                      >
                        <FaPlus />
                      </button>
                    </div>
                  </th>
                </tr>
                {/* Cabeçalho da tabela */}
                <tr>
                  <th className="pb-2 px-3 text-gray-500 dark:text-gray-300 font-semibold bg-gray-50 dark:bg-gray-900 rounded-l-lg text-center">Data</th>
                  <th className="pb-2 px-3 text-gray-500 dark:text-gray-300 font-semibold bg-gray-50 dark:bg-gray-900 text-center">Categoria</th>
                  <th className="pb-2 px-3 text-gray-500 dark:text-gray-300 font-semibold bg-gray-50 dark:bg-gray-900 text-center">Descrição</th>
                  <th className="pb-2 px-3 text-gray-500 dark:text-gray-300 font-semibold bg-gray-50 dark:bg-gray-900 text-center">Valor</th>
                  <th className="pb-2 px-3 text-gray-500 dark:text-gray-300 font-semibold bg-gray-50 dark:bg-gray-900 rounded-r-lg text-center">Ações</th>
                </tr>
              </thead>
              <tbody>
                {loading ? (
                  <tr>
                    <td colSpan={5} className="text-center py-4 text-gray-400 dark:text-gray-500">Carregando...</td>
                  </tr>
                ) : receitasFiltradas.length === 0 ? (
                  <tr>
                    <td colSpan={5} className="text-center py-4 text-gray-400 dark:text-gray-500">Nenhuma receita encontrada.</td>
                  </tr>
                ) : (
                  receitasFiltradas.map((r) => (
                    <tr
                      key={r.id}
                      className="bg-gray-50 dark:bg-gray-900 hover:bg-gray-100 dark:hover:bg-gray-800 transition rounded-lg shadow-sm"
                    >
                      <td className="py-2 px-3 rounded-l-lg font-mono text-sm text-center text-gray-700 dark:text-gray-300">{format(new Date(r.data), "dd/MM/yyyy")}</td>
                      <td className="py-2 px-3 text-center text-gray-700 dark:text-gray-300">{r.categoria.nome}</td>
                      <td className="py-2 px-3 text-center text-gray-700 dark:text-gray-300">{r.descricao}</td>
                      <td className="py-2 px-3 font-semibold text-green-600 dark:text-green-400 text-center">R$ {r.valor.toFixed(2)}</td>
                      <td className="py-2 px-3 rounded-r-lg flex justify-center gap-2">
                        <button
                          className="light:btn-icn-wt dark:btn-icn transition-colors"
                          title="Editar"
                          aria-label="Editar"
                          style={{ border: "none" }}
                        >
                          <FaEdit size={20} />
                        </button>
                        <button
                          className="light:btn-icn-wt dark:btn-icn transition-colors"
                          title="Excluir"
                          aria-label="Excluir"
                          style={{ border: "none" }}
                        >
                          <FaTrash size={20} />
                        </button>
                      </td>
                    </tr>
                  ))
                )}
              </tbody>
            </table>
            <div className="flex justify-center">
              <Pagination
                page={page}
                totalPages={totalPages}
                onPageChange={setPage}
              />
            </div>
          </div>
        </div>
      </div>
      {showModal && (
        <ModalNovaReceita
          onClose={() => {
            setShowModal(false);
            setError(null);
          }}
          onSave={handleSaveReceita}
          error={error}
        />
      )}
      {success && (
        <ModalSucesso
          mensagem={success}
          onClose={() => setSuccess(null)}
        />
      )}
    </>
  );
};

export default Receitas;