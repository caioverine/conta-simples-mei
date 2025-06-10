import { FaChevronLeft, FaChevronRight } from "react-icons/fa";

interface PaginationProps {
  page: number;
  totalPages: number;
  onPageChange: (page: number) => void;
}

const Pagination = ({ page, totalPages, onPageChange }: PaginationProps) => {
  if (totalPages <= 1) return null;

  return (
    <nav
      className="flex items-center gap-1 mt-1"
      aria-label="Paginação"
    >
      <button
        onClick={() => onPageChange(page - 1)}
        disabled={page === 0}
        className={`w-8 h-8 flex items-center justify-center rounded-full border border-gray-300 bg-white text-gray-500 hover:bg-gray-100 transition
          ${page === 0 ? "opacity-50 cursor-not-allowed" : "hover:border-[#234557]"}
        `}
        aria-label="Página anterior"
        tabIndex={page === 0 ? -1 : 0}
      >
        <FaChevronLeft />
      </button>
      {[...Array(totalPages)].map((_, idx) => (
        <button
          key={idx}
          onClick={() => onPageChange(idx)}
          className={`w-8 h-8 flex items-center justify-center rounded-full border
            ${idx === page
              ? "bg-[#234557] text-white border-[#234557] shadow"
              : "bg-white text-gray-700 border-gray-300 hover:bg-gray-100 hover:border-[#234557]"}
            transition font-medium
          `}
          aria-current={idx === page ? "page" : undefined}
        >
          {idx + 1}
        </button>
      ))}
      <button
        onClick={() => onPageChange(page + 1)}
        disabled={page + 1 >= totalPages}
        className={`w-8 h-8 flex items-center justify-center rounded-full border border-gray-300 bg-white text-gray-500 hover:bg-gray-100 transition
          ${page + 1 >= totalPages ? "opacity-50 cursor-not-allowed" : "hover:border-[#234557]"}
        `}
        aria-label="Próxima página"
        tabIndex={page + 1 >= totalPages ? -1 : 0}
      >
        <FaChevronRight />
      </button>
    </nav>
  );
};

export default Pagination;