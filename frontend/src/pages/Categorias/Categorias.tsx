import { useState, useEffect } from "react";
import Navbar from "../../components/Navbar";
import { FaPlus, FaTrash } from "react-icons/fa";
import { excluirCatefgoria, listarCategorias, salvarCategoria } from "../../services/categoria-service";
import type { Categoria } from "../../model/categoria-model";
import ModalNovaCategoria, { type CategoriaFormData } from "./ModalNovaCategoria";
import { ModalSucesso } from "../../components/ModalSucesso";

const Categorias = () => {
  const [categorias, setCategorias] = useState<Categoria[]>([]);
  const [filtroNome, setFiltroNome] = useState("");
  const [loading, setLoading] = useState(false);
  const [showModal, setShowModal] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [success, setSuccess] = useState<string | null>(null);

  useEffect(() => {
    async function fetchCategorias() {
      setLoading(true);
      try {
        const resp = await listarCategorias();
        setCategorias(resp.data);
      } finally {
        setLoading(false);
      }
    }
    fetchCategorias();
  }, []);

  const categoriasFiltradas = categorias.filter((cat) =>
    cat.nome.toLowerCase().includes(filtroNome.toLowerCase())
  );

  const handleSaveCategoria = async (data: CategoriaFormData) => {
    setLoading(true);
    setError(null);
    try {
      await salvarCategoria(data);
      const resp = await listarCategorias();
      setCategorias(resp.data);
      setShowModal(false);
      setSuccess("Categoria salva com sucesso!");
    } catch (err: unknown) {
      console.error("Erro ao salvar categoria:", err);
      setError("Erro ao salvar categoria. Tente novamente.");
    } finally {
      setLoading(false);
    }
  };

  const handleDeleteClick = async (categoria: Categoria) => {
      if (window.confirm(`Deseja realmente excluir a categoria "${categoria.nome}"?`)) {
        setLoading(true);
        setError(null);
        try {
          await excluirCatefgoria(categoria.id);
          setSuccess("Categoria excluída com sucesso!");
          // Recarrega a lista após excluir
          const resp = await listarCategorias();
          setCategorias(resp.data);
        } catch (err: unknown) {
          console.error("Erro ao excluir categoria:", err);
          setError("Erro ao excluir categoria. Tente novamente.");
        } finally {
          setLoading(false);
        }
      }
    };

  return (
    <>
      <Navbar titulo="Categorias" />
      <div className="min-h-screen bg-gray-100 dark:bg-gray-900">
        <div className="container mx-auto px-4 py-8">
          <div className="overflow-x-auto bg-white dark:bg-gray-800 rounded-lg pt-2 pb-2 shadow-sm">
            <table className="min-w-full border-separate border-spacing-y-2">
              <thead>
                {/* Linha de filtro + botão */}
                <tr>
                  <th colSpan={3} className="pb-2 px-3 bg-gray-50 dark:bg-gray-900 rounded-t-lg">
                    <div className="flex flex-row flex-nowrap items-center justify-between gap-2 mt-2">
                      <input
                        type="text"
                        placeholder="Filtrar por nome..."
                        className="rounded border-gray-300 dark:border-gray-700 px-3 py-2 text-sm w-full md:w-64 bg-white dark:bg-gray-900 text-gray-700 dark:text-gray-300"
                        value={filtroNome}
                        onChange={(e) => setFiltroNome(e.target.value)}
                      />
                      <button className="btn-icn flex items-center gap-2 font-medium shrink-0 ml-2 min-w-fit"
                        onClick={() => setShowModal(true)}>
                        <FaPlus />
                      </button>
                    </div>
                  </th>
                </tr>
                {/* Cabeçalho da tabela */}
                <tr>
                  <th className="pb-2 px-3 text-gray-500 dark:text-gray-300 font-semibold bg-gray-50 dark:bg-gray-900 rounded-l-lg text-center">
                    Nome
                  </th>
                  <th className="pb-2 px-3 text-gray-500 dark:text-gray-300 font-semibold bg-gray-50 dark:bg-gray-900 text-center">
                    Tipo
                  </th>
                  <th className="pb-2 px-3 text-gray-500 dark:text-gray-300 font-semibold bg-gray-50 dark:bg-gray-900 rounded-r-lg text-center">
                    Ações
                  </th>
                </tr>
              </thead>
              <tbody>
                {loading ? (
                  <tr>
                    <td colSpan={3} className="text-center py-4 text-gray-400 dark:text-gray-500">
                      Carregando...
                    </td>
                  </tr>
                ) : categoriasFiltradas.length === 0 ? (
                  <tr>
                    <td colSpan={3} className="text-center py-4 text-gray-400 dark:text-gray-500">
                      Nenhuma categoria encontrada.
                    </td>
                  </tr>
                ) : (
                  categoriasFiltradas.map((cat, idx) => (
                    <tr
                      key={cat.id ?? idx}
                      className="bg-gray-50 dark:bg-gray-900 hover:bg-gray-100 dark:hover:bg-gray-800 transition rounded-lg shadow-sm"
                    >
                      <td className="py-2 px-3 rounded-l-lg text-center text-gray-700 dark:text-gray-300">
                        {cat.nome}
                      </td>
                      <td className="py-2 px-3 text-center text-gray-700 dark:text-gray-300">
                        {cat.tipo ?? "-"}
                      </td>
                      <td className="py-2 px-3 rounded-r-lg flex justify-center gap-2">
                        <button
                          className="!p-0 rounded-full text-[#234557] dark:text-gray-200 hover:text-[#2B9669] transition-colors border-none focus:outline-none"
                          title="Excluir"
                          aria-label="Excluir"
                          type="button"
                          style={{ background: "transparent", border: "none" }}
                          onClick={() => handleDeleteClick(cat)}
                        >
                          <FaTrash size={20} />
                        </button>
                      </td>
                    </tr>
                  ))
                )}
              </tbody>
            </table>
          </div>
        </div>
      </div>
      {showModal && (
        <ModalNovaCategoria
          onClose={() => {
            setShowModal(false);
            setError(null);
          }}
          onSave={handleSaveCategoria}
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

export default Categorias;