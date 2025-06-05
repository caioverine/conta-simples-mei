import { ResponsiveContainer, Legend, PieChart, Pie, Cell, Tooltip } from "recharts";

interface PieData {
  name: string;
  value: number;
}

interface DespesasPorCategoriaPieProps {
  data: PieData[];
  colors?: string[];
}

const DEFAULT_COLORS = ["#dc2626", "#f59e42", "#facc15", "#2563eb", "#10b981", "#a21caf"];

export default function DespesasPorCategoriaPie({ data, colors = DEFAULT_COLORS }: DespesasPorCategoriaPieProps) {
  return (
    <div className="bg-white rounded shadow p-6 flex flex-col items-center justify-center">
      <h3 className="text-lg font-semibold text-[#234557] mb-4">Despesas por categoria</h3>
      <ResponsiveContainer width="100%" height={250}>
        <PieChart>
          <Pie
            data={data}
            dataKey="value"
            nameKey="name"
            cx="50%"
            cy="50%"
            outerRadius={80}
            label
          >
            {data.map((entry, index) => (
              <Cell key={`cell-${index}`} fill={colors[index % colors.length]} />
            ))}
          </Pie>
          <Legend />
          <Tooltip />
        </PieChart>
      </ResponsiveContainer>
    </div>
  );
}