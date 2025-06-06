import { useEffect, useState } from "react";
import Navbar from "../../components/Navbar";
import DashboardCards from "./DashboardCards";
import SaldoAcumuladoChart from "./SaldoAcumuladoChart";
import DespesasPorCategoriaPie from "./DespesasPorCategoriaPie";
import UltimasMovimentacoes from "./UltimasMovimentacoes";
import { getDashboardDespesasPorCategoria, getDashboardResumoFinanceiro, getDashboardSaldoMensal, getDashboardUltimasMovimentacoes } from "../../services/dashboard-service";

export default function Dashboard() {
  const [resumo, setResumoFinanceiro] = useState(null);
  const [movimentacoes, setUltimasMovimentacoes] = useState([]);
  const [saldoMensal, setSaldoMensal] = useState([]);
  const [despesasPorCategoria, setDespesasPorCategoria] = useState([]);
  const [loadingCards, setLoadingCards] = useState(true);

   useEffect(() => {
    async function fetchDashboardData() {
      setLoadingCards(true);
      try {
        const [
          resumoResp,
          saldoMensalResp,
          despesasPorCategoriaResp,
          ultimasMovimentacoesResp
        ] = await Promise.all([
          getDashboardResumoFinanceiro(),
          getDashboardSaldoMensal(),
          getDashboardDespesasPorCategoria(),
          getDashboardUltimasMovimentacoes()
        ]);
        setResumoFinanceiro(resumoResp.data);
        setSaldoMensal(saldoMensalResp.data);
        setDespesasPorCategoria(despesasPorCategoriaResp.data);
        setUltimasMovimentacoes(ultimasMovimentacoesResp.data);
      } catch (err) {
        setResumoFinanceiro(null);
        setSaldoMensal([]);
        setDespesasPorCategoria([]);
        setUltimasMovimentacoes([]);
        console.error("Erro ao buscar dados do dashboard:", err);
      } finally {
        setLoadingCards(false);
      }
    }
    fetchDashboardData();
  }, []);

  return (
    <>
      <Navbar titulo="Dashboard" />
      <div className="min-h-screen bg-gray-100">
        <div className="container mx-auto px-4 py-8">
          {/* Cards */}
          <DashboardCards resumo={resumo} loading={loadingCards} />

          {/* Gráficos lado a lado */}
          <div className="grid grid-cols-1 md:grid-cols-2 gap-8 mb-8">
            {/* Gráfico de saldo acumulado mês a mês */}
            <SaldoAcumuladoChart data={saldoMensal} />
            {/* PieChart de despesas por categoria */}
            <DespesasPorCategoriaPie data={despesasPorCategoria} />
          </div>

          {/* Últimas movimentações */}
          <UltimasMovimentacoes movimentacoes={movimentacoes} />
        </div>
      </div>
    </>
  );
}