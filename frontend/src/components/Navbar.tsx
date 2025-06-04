interface NavbarProps {
  titulo: string;
}

export default function Navbar({ titulo }: NavbarProps) {
  return (
    <nav className="w-full bg-[#234557] px-6 py-4 shadow flex items-center justify-center rounded-t-3xl">
      <span className="text-white text-xl font-semibold text-center w-full">{titulo}</span>
    </nav>
  );
}