import React from "react";
import styles from "./TrendingDomains.module.css";

const TrendingDomains = ({ domains, isLoading, error }) => {
  return (
    <div className={styles.container}>
      <h4>Trending Domains</h4>
      {isLoading && <p>Loading trends...</p>}
      {error && <p className={styles.error}>{error}</p>}
      {!isLoading && !error && (
        <ul className={styles.list}>
          {domains.length === 0 ? (
            <li>No trending data available yet.</li>
          ) : (
            domains.map((item) => (
              <li key={item.domain} className={styles.listItem}>
                <span className={styles.domain}>{item.domain}</span>
                <span className={styles.score}>{item.score}</span>
              </li>
            ))
          )}
        </ul>
      )}
    </div>
  );
};

export default TrendingDomains;
