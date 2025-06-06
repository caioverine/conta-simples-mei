import api from "./api";

export const getDashboardResumoFinanceiro = async () => {
  return api.get("/dashboard/resumo");
};

export const getDashboardSaldoMensal = async () => {
  return api.get("/dashboard/saldo-mensal");
};

export const getDashboardDespesasPorCategoria = async () => {
  return api.get("/dashboard/despesas-categoria");
};

export const getDashboardUltimasMovimentacoes = async () => {
  return api.get("/dashboard/ultimas-movimentacoes");
};