
import type { ReactNode } from "react";
import Sidebar from "./Sidebar";

interface LayoutProps {
  children: ReactNode;
}

export default function Layout({ children }: LayoutProps) {
  return (
    <section className="bg-white dark:bg-gray-900">
      <div className="flex h-screen">
        <Sidebar />
        <div className="flex-1 flex flex-col bg-gray-100 dark:bg-gray-800">
            <main >{children}</main>
        </div>
      </div>
    </section>
  );
}