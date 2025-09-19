import React from "react";
import { Link, useNavigate } from "react-router-dom";
import { useUserStore } from "../store/userStore";
import styles from "./Navbar.module.css";

const Navbar = () => {
  const { isAuthenticated, logout } = useUserStore();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  return (
    <nav className={styles.navbar}>
      <div className={styles.navContainer}>
        <Link
          to={isAuthenticated ? "/dashboard" : "/"}
          className={styles.brand}
        >
          🛡️ LinkGuard
        </Link>
        <div className={styles.navLinks}>
          {isAuthenticated ? (
            <button onClick={handleLogout} className={styles.navButton}>
              Logout
            </button>
          ) : (
            <>
              <Link to="/login" className={styles.navLink}>
                Login
              </Link>
              <Link to="/register" className={styles.navLink}>
                Register
              </Link>
            </>
          )}
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
