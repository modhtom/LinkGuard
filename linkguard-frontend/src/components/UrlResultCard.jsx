import React from "react";
import styles from "./UrlResultCard.module.css";

const UrlResultCard = ({ result }) => {
  if (!result) return null;

  return (
    <div className={styles.card}>
      <h3 className={styles.title}>{result.title || "No Title Found"}</h3>
      <p className={styles.description}>
        {result.description || "No description found."}
      </p>
      <div className={styles.details}>
        <p>
          <strong>Short URL:</strong>{" "}
          <a href={result.shortUrl} target="_blank" rel="noopener noreferrer">
            {result.shortUrl}
          </a>
        </p>
        <p>
          <strong>Final URL:</strong>{" "}
          <a href={result.finalUrl} target="_blank" rel="noopener noreferrer">
            {result.finalUrl}
          </a>
        </p>
        <p>
          <strong>Domain:</strong> <span>{result.domain}</span>
        </p>
      </div>
    </div>
  );
};

export default UrlResultCard;
