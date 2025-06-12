import type { Categoria } from "./categoria-model";

export interface Despesa {
  id: string;
  categoria: Categoria;
  descricao: string;
  valor: number;
  data: string;
}