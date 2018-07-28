(define duplicate (lambda (l) (cond ((eq l ()) ())
                                    (t (cons (car l) (cons (car l) (duplicate (cdr l))))))))

(duplicate (quote (a b c d e)))

(duplicate (quote ((a) (b) (c))))

(duplicate (quote ()))
