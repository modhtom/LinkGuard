import React, { useState } from 'react';
import styles from './UrlForm.module.css';

const UrlForm = ({ onExpand, isLoading }) => {
  const [url, setUrl] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    onExpand(url);
  };

  return (
    <form onSubmit={handleSubmit} className={styles.form}>
      <input
        type="text"
        value={url}
        onChange={(e) => setUrl(e.target.value)}
        placeholder="Enter a shortened URL to expand..."
        className={styles.input}
        disabled={isLoading}
      />
      <button type="submit" className={styles.button} disabled={isLoading}>
        {isLoading ? 'Expanding...' : 'Expand'}
      </button>
    </form>
  );
};

export default UrlForm;
