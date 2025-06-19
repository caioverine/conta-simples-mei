import type { ReactNode } from "react";
import Sidebar from "./Sidebar";

interface LayoutProps {
  children: ReactNode;
}

export default function Layout({ children }: LayoutProps) {
  return (
    <section className="bg-white dark:bg-gray-900">
      <Sidebar />
      <div 
        style={{ marginLeft: "var(--sidebar-width)" }}
        className="flex flex-col min-h-screen bg-gray-100 dark:bg-gray-800">
        <main>{children}</main>
      </div>
    </section>
  );
}