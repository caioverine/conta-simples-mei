import type { Despesa } from "./despesa-model";

export interface DespesaPage {
  content: Despesa[];
  totalPages: number;
  totalElements: number;
  number: number;
  size: number;
  last: boolean;
  first: boolean;
  empty: boolean;
}